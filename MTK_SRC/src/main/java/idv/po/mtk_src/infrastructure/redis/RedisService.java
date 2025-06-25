package idv.po.mtk_src.infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import idv.po.mtk_src.booking.seat.Seat;
import idv.po.mtk_src.booking.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StringUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String LOGIN_PREFIX = "login:token:";


    private final RedisTemplate<String, String> redisTemplate;

    private final SeatRepository seatRepository;


    public void cacheToken(String token, String email, long ttlMillis) {
        redisTemplate.opsForValue().set(LOGIN_PREFIX + token, email, Duration.ofMillis(ttlMillis));
    }

    public boolean isValidToken(String token) {
        return redisTemplate.hasKey(LOGIN_PREFIX + token);
    }

    public void removeToken(String token) {
        redisTemplate.delete(LOGIN_PREFIX + token);
    }




    public Map<String, Boolean> getAvailableSeats(UUID screenId,UUID showtimeId) throws JsonProcessingException {
        String redisKey = "showtime:" + showtimeId + ":seats";
        String  seatStatus= redisTemplate.opsForValue().get(redisKey);


        if (seatStatus != null&&!seatStatus.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
           return objectMapper.readValue(seatStatus, new TypeReference<Map<String, Boolean>>() {});
        }

        List<Seat> availableSeats = seatRepository.findSeatsByScreenAndShowtime(screenId,showtimeId);

        Map<String, Boolean> seatStatusMap = availableSeats.stream()
                .collect(Collectors.toMap(
                        seat -> seat.getRowLabel() + "-" + seat.getSeatNo(),
                        seat -> true
                ));


        // 寫入 Redis，設定過期時間
        ObjectMapper objectMapper = new ObjectMapper();
        String seatStatusJson = objectMapper.writeValueAsString(seatStatusMap);
        redisTemplate.opsForValue().set(redisKey, seatStatusJson, 30, TimeUnit.SECONDS);



        return seatStatusMap;
    }







}

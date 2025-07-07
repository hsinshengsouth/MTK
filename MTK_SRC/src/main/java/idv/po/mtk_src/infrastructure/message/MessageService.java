package idv.po.mtk_src.infrastructure.message;

import idv.po.mtk_src.booking.event.BookingSuccessEvent;
import idv.po.mtk_src.booking.vo.SeatInfo;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

  private final JavaMailSender mailSender;

  public void sendBookingSuccessEmail(BookingSuccessEvent event) {

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      String to = event.memberEmail();
      String subject = "訂票成功通知";

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(generateMailContent(event), true); // true = html 格式

      mailSender.send(message);

    } catch (Exception e) {
      throw new RuntimeException("Send mail fail", e);
    }
  }

  private String generateMailContent(BookingSuccessEvent event) {
    StringBuilder seatHtml = new StringBuilder();
    for (SeatInfo seat : event.seats()) {
      seatHtml.append(String.format("<li>第 %s 排 %d 號</li>", seat.getRowLabel(), seat.getSeatNo()));
    }

    return String.format(
        """
                <html>
                <body>
                    <h3>親愛的會員 %s 您好：</h3>
                    <p>您已於 <b>%s</b> 完成訂票，內容如下：</p>
                    <ul>
                        <li>電影名稱：%s</li>
                        <li>戲院名稱：%s</li>
                        <li>場次時間：%s</li>
                        <li>座位：<ul>%s</ul></li>
                        <li>總金額：NT$ %s 元</li>
                    </ul>
                    <p>請持本信件至現場或自助機領取票券，祝您觀影愉快！</p>
                </body>
                </html>
                """,
        event.memberName(),
        event.createTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
        event.movieName(),
        event.screenName(),
        event.showTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
        seatHtml,
        event.totalPrice());
  }
}

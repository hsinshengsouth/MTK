package idv.po.mtk_src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "idv.po.mtk_src")
public class MtkSrcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtkSrcApplication.class, args);
    }

}

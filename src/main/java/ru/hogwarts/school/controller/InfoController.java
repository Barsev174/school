import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/getPort")
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(port);
    }

    @GetMapping("/sum")
    public int getSum() {
        long time = System.currentTimeMillis();
        Stream.iterate(1,a->a+1).limit(1_000_000)
                .reduce(0,Integer::sum);
        time = System.currentTimeMillis() - time;
        return (int) time;
    }
}
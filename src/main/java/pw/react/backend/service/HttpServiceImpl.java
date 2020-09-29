package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pw.react.backend.web.Quote;

@Service
public class HttpServiceImpl implements HttpService {

    private final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    public HttpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void consume(String url) {
        final Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        if (quote != null) {
            logger.info("This is Quote: {}", quote.toString());
        } else {
            logger.warn("Quote is null");
        }
    }
}

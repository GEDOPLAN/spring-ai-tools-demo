package de.gedoplan.showcase.springaidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ToolsAiResource {

    @Autowired
    ChatClient chatClient;

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE, value = "/tools")
    public String tools(@RequestBody String question) {
        return chatClient
                .prompt()
                .user(question)
                .functions("trafficJamFunction", "weatherFunction")
//                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }
}

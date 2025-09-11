package com.Smart_Contact_Manager.demo.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Message {

    private String content;
    @Builder.Default
    private MessageType type = MessageType.blue;

}

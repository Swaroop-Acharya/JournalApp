package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Email {
    @Id
    private String id;
    @NonNull
    private String to;

    @NonNull
    private String subject;

    @NonNull
    private String body;


}

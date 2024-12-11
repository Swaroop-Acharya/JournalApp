package net.engineeringdigest.journalApp.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "journal_entries")
@Getter
@Setter
@NoArgsConstructor
public class JournalEntry implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;
}

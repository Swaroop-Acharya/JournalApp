package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor

public class Product {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String des;

    @Override
    public String toString(){
        return "id"+this.id+"\n"+"name"+this.name+"\n"+"des"+this.des+"\n";
    }
}

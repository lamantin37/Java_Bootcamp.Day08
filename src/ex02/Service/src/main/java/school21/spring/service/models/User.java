package school21.spring.service.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long Identifier;
    private String Email;
    private String Password;
}

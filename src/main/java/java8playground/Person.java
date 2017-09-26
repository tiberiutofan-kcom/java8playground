package java8playground;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Person {
    private final String id = UUID.randomUUID().toString();
    private final String firstName;
    private final String lastName;
    private final Integer yearOfBirth;
}
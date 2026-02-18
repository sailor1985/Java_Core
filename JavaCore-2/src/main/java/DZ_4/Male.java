package DZ_4;

import lombok.Getter;

@Getter
public enum Male {
    MALE("мужчина"), FEMALE("женщина");
    private final String genderName;

    Male(String genderName) {
        this.genderName = genderName;
    }
}
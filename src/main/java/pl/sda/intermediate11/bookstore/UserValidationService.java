package pl.sda.intermediate11.bookstore;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class UserValidationService {


    public static final String FIRST_NAME_VAL_RES = "firstNameValRes";
    public static final String LAST_NAME_VAL_RES = "lastNameValRes";
    public static final String ZIP_CODE_VAL_RES = "zipCodeValRes";

    public Map<String, String> validateUserData(UserRegistrationDTO userRegistrationDTO) {

        Map<String, String> resultMap = Maps.newHashMap();
        if (namePartIsNotValid(userRegistrationDTO.getFirstName(), "[A-Z][a-z]{2,}")) {
            resultMap.put(FIRST_NAME_VAL_RES, "Imię jest wymagane.");
        }
        if (namePartIsNotValid(userRegistrationDTO.getLastName(), "[A-Z][a-z]{2,}(|-[A-Z][a-z]{2,})")) {
            resultMap.put(LAST_NAME_VAL_RES, "Nazwisko jest wymagane.");
        }

        if (zipCodeIsNotValid(userRegistrationDTO.getUserAddress().getZipCode())) {
            resultMap.put(ZIP_CODE_VAL_RES, "Kod pocztowy jest wymagany.");
        }
        if (addressPartIsNotValid(userRegistrationDTO.getUserAddress().getCity())) {

        }
        if (addressPartIsNotValid(userRegistrationDTO.getUserAddress().getCountry())) {
        }

        return resultMap;
    }

    private boolean addressPartIsNotValid(String city) {
        return StringUtils.isBlank(city);
    }

    private boolean zipCodeIsNotValid(String zipCode) {
        return StringUtils.isBlank(zipCode)
                || !zipCode.trim().matches("[0-9]{2}-[0-9]{3}");
    }

    private boolean namePartIsNotValid(String sameName, String regex) {
        return StringUtils.isBlank(sameName)
                || !sameName.trim().matches(regex);
    }

}

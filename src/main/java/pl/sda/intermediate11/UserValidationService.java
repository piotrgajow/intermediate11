package pl.sda.intermediate11;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserValidationService {


    public static final String FIRST_NAME_VAL_RES = "firstNameValRes";

    public Map<String, String> validateUserData(UserRegistrationDTO userRegistrationDTO){

        Map<String, String> resultMap = Maps.newHashMap();
        if(StringUtils.isBlank(userRegistrationDTO.getFirstName())
                || !userRegistrationDTO.getFirstName().trim().matches("[A-Z][a-z]{2,}")){
            resultMap.put(FIRST_NAME_VAL_RES, "Imię jest wymagane.");
        }

        return resultMap;
    }

}

package validators;

import java.util.ArrayList;
import java.util.List;

public class WarValidator implements IValidator {


    private final List<String> errors;
    private boolean found;

    public WarValidator(boolean found){
        errors = new ArrayList<>();
        this.found = found;
    }

    @Override
    public boolean validate() {
        if(found == false)
            errors.add("Reqiment of this type is not required here");
        return errors.isEmpty();
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }
}

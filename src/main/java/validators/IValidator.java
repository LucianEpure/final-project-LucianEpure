package validators;

import java.util.List;

public interface IValidator {
	public boolean validate();
	
	public List<String> getErrors();
}

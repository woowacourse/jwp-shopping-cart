package cart.validation;

import javax.validation.GroupSequence;

import cart.validation.ValidationGroups.NotBlankGroup;
import cart.validation.ValidationGroups.PatternCheckGroup;

@GroupSequence({NotBlankGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}

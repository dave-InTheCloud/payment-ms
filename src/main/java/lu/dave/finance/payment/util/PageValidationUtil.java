package lu.dave.finance.payment.util;

import lu.dave.finance.payment.exception.BadParameterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


// it would be better to do a custom annotation for pageable or post validator (to be improved)
public class PageValidationUtil {
    private PageValidationUtil(){
        throw new IllegalStateException("Utility class");
    }
    public static void validatePageNumber(Page<?> customerEntityPage, Pageable pageable) {
        if (customerEntityPage.getTotalPages() < pageable.getPageNumber())
            throw new BadParameterException(
                    String.format("You requested a page greater then the maximun number of page:  %s", pageable.getPageNumber()));
    }

    public static void pageIsNegative(Pageable pageable) {
        // by default return the first page 0
        if(pageable.getPageNumber() < 0) throw new BadParameterException("Provide a pagination greater or equal to 0");
    }
}

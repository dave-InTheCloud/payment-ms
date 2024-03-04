package lu.dave.finance.payment.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageableDto {
    private int page;
    private int pageSize;
    private int totalPage;
    private Long totalElement;

    public PageableDto(Page<?> page) {
      this.page = page.getNumber();
      this.pageSize = page.getSize();
      this.totalPage = page.getTotalPages();
      this.totalElement = page.getTotalElements();
    }
}

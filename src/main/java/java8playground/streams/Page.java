package java8playground.streams;

import java.util.Collection;

public class Page<T> {
    private Collection<T> currentPage;
    private Integer offset;
    private Integer limit;
    private Integer totalCount;

    public Page() {
    }

    public Collection<T> getCurrentPage() {
        return this.currentPage;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public static class Builder<T> {
        private Collection<T> currentPage;
        private Integer offset;
        private Integer limit;
        private Integer totalCount;

        private Builder() {
        }

        public static <T> Page.Builder<T> page() {
            return new Page.Builder();
        }

        public static <T> Page.Builder<T> page(Class<T> type) {
            return new Page.Builder();
        }

        public Page.Builder<T> withCurrentPage(Collection<T> currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Page.Builder<T> withOffset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public Page.Builder<T> withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Page.Builder<T> withTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Page<T> build() {
            Page page = new Page();
            page.currentPage = this.currentPage;
            page.offset = this.offset;
            page.limit = this.limit;
            page.totalCount = this.totalCount;
            return page;
        }
    }

}

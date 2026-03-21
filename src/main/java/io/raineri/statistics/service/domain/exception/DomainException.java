package io.raineri.statistics.service.domain.exception;

public abstract class DomainException  extends RuntimeException {
    private final CodeException code;
    private final SubcodeException subcode;

    protected DomainException(CodeException code, SubcodeException subcode) {
        super(subcode.getMessage());
        this.code = code;
        this.subcode = subcode;
    }

    public CodeException getCode() { return code; }
    public SubcodeException getSubcode() { return subcode; }
}

package totp;

class TotpException extends Exception {

    public TotpException(String message, Exception exception) {
        super(message, exception);
    }

}

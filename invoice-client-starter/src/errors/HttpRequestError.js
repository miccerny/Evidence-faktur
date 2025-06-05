export default class HttpRequestError extends Error {
    constructor(message, status) {
        super(message);
        this.name = "HttpRequestError";
        this.status = status;
    }
}
export default class HttpRequestError extends Error {
    constructor(message, status, data =null) {
        super(message);
        this.name = "HttpRequestError";
        this.status = status;
        this.data = data;
    }
}
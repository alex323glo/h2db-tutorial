package com.alex323glo.tutor.h2db.part_2.model.response;

/**
 * Response model.
 *
 * @see T type of response body.
 *
 * @author alex323glo
 * @version 1.0
 */
public class Response<T> {

    private ResponseStatus status;
    private T body;

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public Response(ResponseStatus status, T body) {
        this.status = status;
        this.body = body;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response<?> response = (Response<?>) o;

        if (status != response.status) return false;
        return body != null ? body.equals(response.body) : response.body == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", body=" + body +
                '}';
    }
}

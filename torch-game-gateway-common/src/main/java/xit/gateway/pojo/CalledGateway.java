package xit.gateway.pojo;

public class CalledGateway {
    private String host;
    private String port;
    private String uri;

    public CalledGateway() {
    }

    public CalledGateway(String host, String port, String uri) {
        this.host = host;
        this.port = port;
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "CalledGateway{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}

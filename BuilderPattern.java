package design.patterns;

class URLBuilder {

    //The Builder pattern is used to construct a complex object step by step.
    // It allows the construction of different types and representations of an object using the same construction code.
    private String protocol;
    private String host;
    private int port;
    private String path;
    private String query;

    private URLBuilder(Builder builder) {
        this.protocol = builder.protocol;
        this.host = builder.host;
        this.port = builder.port;
        this.path = builder.path;
        this.query = builder.query;
    }

    @Override
    public String toString() {
        StringBuilder url = new StringBuilder();
        url.append(protocol).append("://").append(host);
        if (port > 0) {
            url.append(":").append(port);
        }
        if (path != null && !path.isEmpty()) {
            url.append("/").append(path);
        }
        if (query != null && !query.isEmpty()) {
            url.append("?").append(query);
        }
        return url.toString();
    }

    static class Builder {
        private String protocol;
        private String host;
        private int port = -1; // default to -1 indicating no port specified
        private String path;
        private String query;

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public URLBuilder build() {
            if (protocol == null || host == null) {
                throw new IllegalStateException("Protocol and host must be specified");
            }
            return new URLBuilder(this);
        }
    }
}

public class BuilderPattern {
    public static void main(String[] args) {

    }
}


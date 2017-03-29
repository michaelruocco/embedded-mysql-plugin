package uk.co.mruoc.mysql

class UrlParser {

    static int extractPort(String url) {
        URI uri = toUri(url)
        return uri.getPort()
    }

    static String extractDatabaseName(String url) {
        URI uri = toUri(url)
        return removeForwardSlash(uri.getPath())
    }

    private static URI toUri(String url) {
        String cleanUrl = removeJdbcPrefix(url)
        return URI.create(cleanUrl)
    }

    private static String removeJdbcPrefix(String s) {
        return s.replaceAll("jdbc:", "")
    }

    private static String removeForwardSlash(String s) {
        return s.replaceAll("/", "")
    }

}

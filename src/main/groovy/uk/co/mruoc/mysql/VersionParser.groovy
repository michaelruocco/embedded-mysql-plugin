package uk.co.mruoc.mysql

import com.wix.mysql.distribution.Version

class VersionParser {

    static Version parse(String version) {
        try {
            return Version.valueOf(version)
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(getInvalidVersionMessage(version), e)
        }
    }

    private static String getInvalidVersionMessage(String invalidVersion) {
        StringBuilder s = new StringBuilder()
        s.append("invalid version specified: ")
        s.append(invalidVersion)
        s.append(" possible valid versions are: [ ")
        s.append(toNames(Version.values()).join(", "))
        s.append(" ]")
        return s.toString()
    }

    private static List<String> toNames(Version[] versions) {
        List<String> names = new ArrayList<>()
        versions.each { v -> names.add(v.name()) }
        return names;
    }

}

package com.chakans.blog.config.constants;

/**
 * Constants for Blog Enums.
 */
public class BlogEnumsConstants {

    public enum BLOG_SATAUS {

        TRASHED("TRASHED", 0),
        CLOSED("CLOSED", 1),
        OPENED("OPENED", 2);

        private final String name;
        private final int value;

        BLOG_SATAUS(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static BLOG_SATAUS getEnum(String name) {
            try {
                return BLOG_SATAUS.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum PAGE_SATAUS {

        TRASHED("TRASHED", 0),
        DRAFT("DRAFT", 1),
        PUBLISHED("PUBLISHED", 2),
        SECRET("SECRET", 3);

        private final String name;
        private final int value;

        PAGE_SATAUS(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static PAGE_SATAUS getEnum(String name) {
            try {
                return PAGE_SATAUS.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum POST_SATAUS {

        TRASHED("TRASHED", 0),
        DRAFT("DRAFT", 1),
        PUBLISHED("PUBLISHED", 2),
        SECRET("SECRET", 3);

        private final String name;
        private final int value;

        POST_SATAUS(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static POST_SATAUS getEnum(String name) {
            try {
                return POST_SATAUS.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum COMMENT_SATAUS {

        TRASHED("TRASHED", 0),
        PARENTTRASHED("PARENTTRASHED", 1),
        PUBLISHED("PUBLISHED", 2),
        SECRET("SECRET", 3),
        SPAM("SPAM", 4);

        private final String name;
        private final int value;

        COMMENT_SATAUS(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static COMMENT_SATAUS getEnum(String name) {
            try {
                return COMMENT_SATAUS.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum COMMENT_OBJECT_TYPE {

        PAGE("PAGE", 0),
        POST("POST", 1);

        private final String name;
        private final int value;

        COMMENT_OBJECT_TYPE(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static COMMENT_OBJECT_TYPE getEnum(String name) {
            try {
                return COMMENT_OBJECT_TYPE.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum THEME_SATAUS {

        TRASHED("TRASHED", 0),
        DRAFT("DRAFT", 1),
        PUBLISHED("PUBLISHED", 2),
        WAITPUBLISHED("WAITPUBLISHED", 3);

        private final String name;
        private final int value;

        THEME_SATAUS(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static THEME_SATAUS getEnum(String name) {
            try {
                return THEME_SATAUS.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public BlogEnumsConstants() {
    }
}

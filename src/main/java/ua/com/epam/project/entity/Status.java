package ua.com.epam.project.entity;

/**
 * Enum for statuses
 *
 * @author Denis Davydov
 * @version 2.0
 */
public enum Status {
    ACTIVE, BANNED;

    public enum CourseStatus {
        NOT_STARTED, CURRENT, FINISHED
    }
}
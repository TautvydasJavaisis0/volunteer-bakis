package com.tietoevry.backend.util;

import com.tietoevry.backend.initiative.comment.model.Question;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;

import java.util.Date;

public class ValidationUtil {

    public static boolean doesAnswerExists(Question question) {
        if (question.getAnswer() != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameInitiative(Initiative initiative1, Initiative initiative2) {
        if (initiative1.getId() == initiative2.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOwner(Initiative initiative, User user) {
        if (initiative.getUser().getId() == user.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isApplied(Initiative initiative, User user) {
        if (initiative.getCandidate().contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInitiativeFull(Initiative initiative) {
        if (initiative.getCurrentNumberOfVolunteers() == initiative.getTotalNumberOfVolunteers()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasInitiativeEnded(Initiative initiative) {
        if (initiative.getEndDate().before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPhoneNumber(String number) {
        if (number.contains("+")){
            return true;
        } else {
            return false;
        }
    }
}

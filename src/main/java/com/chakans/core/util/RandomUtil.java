package com.chakans.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private static final int LOOP_COUNT = 10;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password.
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key.
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key.
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate an UUID
     *
     * @return  the generated UUID
     */
    public static String getRandomUUID(Object allocatedRepository, String methodName) {
        String randomUUID = null;
        for (int i = 0; i < LOOP_COUNT; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            try {
                Method method =  allocatedRepository.getClass().getMethod(methodName, String.class);
                Optional<?> optional = (Optional<?>) method.invoke(allocatedRepository, uuid);
                if (!optional.isPresent()) {
                    randomUUID = uuid;
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                break;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        return randomUUID;
    }

    /**
     * Generate an UUID for User
     *
     * @return  the generated UUID for User
     */
    public static String getRandomUUIDForUser(Object allocatedRepository, String methodName, String userLogin) {
        String randomUUID = null;
        for (int i = 0; i < LOOP_COUNT; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            try {
                Method method =  allocatedRepository.getClass().getMethod(methodName, String.class, String.class);
                Optional<?> optional = (Optional<?>) method.invoke(allocatedRepository, uuid, userLogin);
                if (!optional.isPresent()) {
                    randomUUID = uuid;
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                break;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        return randomUUID;
    }
}

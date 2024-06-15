package alpha.net.utility.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.persistence.PersistenceException;
import java.util.function.Supplier;

@Slf4j
public class ExceptionHandlerUtils {

    @FunctionalInterface
    public interface ThrowableSupplier<T> {
        T get() throws Exception;
    }

    /**
     * A utility method to handle transactional exceptions with custom exception suppliers.
     *
     * @param logic                     The primary logic to execute.
     * @param dataIntegrityViolationSupplier Supplies an exception for DataIntegrityViolationException.
     * @param persistenceExceptionSupplier Supplies an exception for PersistenceException.
     * @param unknownExceptionSupplier   Supplies an exception for unexpected exceptions.
     * @param logMessage                 The log message for this operation.
     * @param logContext                 The context to include in the logs.
     * @param <T>                        The type of the return value from the logic.
     * @return The result of the logic, if successful.
     * @throws Exception Throws exceptions as supplied by the suppliers.
     */
    public static <T> T handleTransactionExceptions(
            ThrowableSupplier<T> logic,
            Supplier<Exception> dataIntegrityViolationSupplier,
            Supplier<Exception> persistenceExceptionSupplier,
            Supplier<Exception> unknownExceptionSupplier,
            String logMessage,
            Object logContext
    ) throws Exception {
        try {
            return logic.get();
        } catch (DataIntegrityViolationException e) {
            log.error("{} -> Data integrity violation: {}", logMessage, logContext, e);
            throw dataIntegrityViolationSupplier.get();
        } catch (PersistenceException e) {
            log.error("{} -> Persistence exception: {}", logMessage, logContext, e);
            throw persistenceExceptionSupplier.get();
        } catch (Exception e) {
            log.error("{} -> Unexpected exception: {}", logMessage, logContext, e);
            throw unknownExceptionSupplier.get();
        }
    }

    //deletion
    public static <T> void handleTransactionExceptions(
            ThrowableSupplier<T> logic,
            Supplier<Exception> dataIntegrityViolationSupplier,
            Supplier<Exception> persistenceExceptionSupplier,
            Supplier<Exception> unknownExceptionSupplier,
            String logMessage
    ) throws Exception {
        try {
            logic.get();
        } catch (DataIntegrityViolationException e) {
            log.error("{} -> Data integrity violation occurred", logMessage, e);
            throw dataIntegrityViolationSupplier.get();
        } catch (PersistenceException e) {
            log.error("{} -> Persistence exception occurred", logMessage, e);
            throw persistenceExceptionSupplier.get();
        } catch (Exception e) {
            log.error("{} -> Unexpected exception occurred", logMessage, e);
            throw unknownExceptionSupplier.get();
        }
    }

    //fetching
    public static <T> T handleTransactionExceptions(
            ThrowableSupplier<T> logic,
            Supplier<Exception> persistenceExceptionSupplier,
            Supplier<Exception> unknownExceptionSupplier,
            String logMessage
    ) throws Exception {
        try {
            return logic.get();
        } catch (PersistenceException e) {
            log.error("{} -> Persistence exception", logMessage, e);
            throw persistenceExceptionSupplier.get();
        } catch (Exception e) {
            log.error("{} -> Unexpected exception", logMessage, e);
            throw unknownExceptionSupplier.get();
        }
    }



}


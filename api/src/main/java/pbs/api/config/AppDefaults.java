package pbs.api.config;

final class AppDefaults {

  private AppDefaults() {}

  static final class Authentication {
    private Authentication() {}

    static final long JWT_EXPIRATION_IN_SEC = 3600000; // 1 hour
    static final long JWT_EXPIRATION_IN_SEC_REMEMBER_ME = 604800000; // 7 hours
  }

  static final class Async {
    private Async() {}

    static final int CORE_POOL_SIZE = 2;
    static final int MAX_POOL_SIZE = 50;
    static final int QUEUE_CAPACITY = 10000;
    static final String THREAD_NAME_PREFIX = "Async-";
  }

  static final class Scheduler {
    private Scheduler() {}

    static final int CORE_POOL_SIZE = 2;
    static final String THREAD_NAME_PREFIX = "Scheduler-";
  }

  static final class Storage {
    private Storage() {}

    static final String LOCATION = "./files/";
  }
}

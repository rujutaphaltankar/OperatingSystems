#include <iostream>
#include <pthread.h>
#include <unistd.h>
#include <vector>
#include <cstdlib>

using namespace std;

int data1 = 0;
int data2 = 0;

pthread_rwlock_t rwlock;

bool synchronization = false;

// ================= READER =================
void* reader(void* arg) {
    int id = *(int*)arg;

    for (int i = 0; i < 3; i++) {
        if (synchronization)
            pthread_rwlock_rdlock(&rwlock);

        int a = data1;

        // Delay to demonstrate race condition
        usleep(2000);

        int b = data2;

        cout << "Reader " << id
             << " reads: " << a << " " << b;

        if (a != b)
            cout << "  <-- RACE CONDITION";

        cout << endl;

        if (synchronization)
            pthread_rwlock_unlock(&rwlock);

        usleep(2000);
    }

    return NULL;
}

// ================= WRITER =================
void* writer(void* arg) {
    int id = *(int*)arg;

    for (int i = 0; i < 3; i++) {
        if (synchronization)
            pthread_rwlock_wrlock(&rwlock);

        int value = rand() % 100 + 1;

        data1 = value;

        // Delay to demonstrate race condition
        usleep(3000);

        data2 = value;

        cout << "Writer " << id
             << " writes: " << data1
             << " " << data2 << endl;

        if (synchronization)
            pthread_rwlock_unlock(&rwlock);

        usleep(2000);
    }

    return NULL;
}

// ================= EXECUTION =================
void executeProgram(int readersCount, int writersCount) {
    pthread_rwlock_init(&rwlock, NULL);

    // Reset shared data for every execution
    data1 = 0;
    data2 = 0;

    vector<pthread_t> readers(readersCount);
    vector<pthread_t> writers(writersCount);

    vector<int> readerID(readersCount);
    vector<int> writerID(writersCount);

    // Create reader threads
    for (int i = 0; i < readersCount; i++) {
        readerID[i] = i + 1;

        pthread_create(&readers[i], NULL, reader, &readerID[i]);
    }

    // Create writer threads
    for (int i = 0; i < writersCount; i++) {
        writerID[i] = i + 1;

        pthread_create(&writers[i], NULL, writer, &writerID[i]);
    }

    // Wait for readers
    for (int i = 0; i < readersCount; i++)
        pthread_join(readers[i], NULL);

    // Wait for writers
    for (int i = 0; i < writersCount; i++)
        pthread_join(writers[i], NULL);

    pthread_rwlock_destroy(&rwlock);

    cout << "\nExecution completed.\n";
}

int main() {
    int readersCount, writersCount, choice;

    cout << "========================================\n";
    cout << "     READER-WRITER SYNCHRONIZATION\n";
    cout << "========================================\n";

    cout << "Enter number of readers: ";
    cin >> readersCount;

    cout << "Enter number of writers: ";
    cin >> writersCount;

    if (readersCount <= 0 || writersCount <= 0) {
        cout << "Number of readers and writers must be greater than 0.\n";
        return 0;
    }

    // Menu after execution
    do {
        cout << "\n========================================\n";
        cout << "              MENU\n";
        cout << "==========================================\n";
        cout << "1. With Synchronization\n";
        cout << "2. Without Synchronization\n";
        cout << "3. Exit\n";
        cout << "Enter your choice: ";
        cin >> choice;

        switch (choice) {
            case 1:
                cout << "\n--- WITH SYNCHRONIZATION ---\n";
                synchronization = true;
                executeProgram(readersCount, writersCount);
                break;

            case 2:
                cout << "\n--- WITHOUT SYNCHRONIZATION ---\n";
                synchronization = false;
                executeProgram(readersCount, writersCount);
                break;

            case 3:
                cout << "\nExiting program...\n";
                break;

            default:
                cout << "\nInvalid choice! Please enter 1, 2 or 3.\n";
        }

    } while (choice != 3);

    return 0;
}    
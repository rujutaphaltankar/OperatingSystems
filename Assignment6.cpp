#include <iostream>
using namespace std;

// First Fit
void firstFit(int blocks[], int blockCount, int processes[], int processCount) {
    int remaining[100];
    int allocation[100];

    for (int i = 0; i < blockCount; i++) {
        remaining[i] = blocks[i];
    }

    for (int i = 0; i < processCount; i++) {
        allocation[i] = -1;

        for (int j = 0; j < blockCount; j++) {
            if (remaining[j] >= processes[i]) {
                allocation[i] = j;
                remaining[j] = remaining[j] - processes[i];
                break;
            }
        }
    }

    cout << "\nFirst Fit:\n";
    cout << "Process\tSize\tBlock\n";

    for (int i = 0; i < processCount; i++) {
        cout << "P" << i + 1 << "\t" << processes[i] << "\t";

        if (allocation[i] == -1) {
            cout << "Not Allocated";
        }
        else {
            cout << "B" << allocation[i] + 1;
        }

        cout << endl;
    }
}

// Best Fit
void bestFit(int blocks[], int blockCount, int processes[], int processCount) {
    int remaining[100];
    int allocation[100];

    for (int i = 0; i < blockCount; i++) {
        remaining[i] = blocks[i];
    }

    for (int i = 0; i < processCount; i++) {
        allocation[i] = -1;
        int best = -1;

        for (int j = 0; j < blockCount; j++) {
            if (remaining[j] >= processes[i]) {
                if (best == -1 || remaining[j] < remaining[best]) {
                    best = j;
                }
            }
        }

        if (best != -1) {
            allocation[i] = best;
            remaining[best] = remaining[best] - processes[i];
        }
    }

    cout << "\nBest Fit:\n";
    cout << "Process\tSize\tBlock\n";

    for (int i = 0; i < processCount; i++) {
        cout << "P" << i + 1 << "\t" << processes[i] << "\t";

        if (allocation[i] == -1) {
            cout << "Not Allocated";
        }
        else {
            cout << "B" << allocation[i] + 1;
        }

        cout << endl;
    }
}

// Worst Fit
void worstFit(int blocks[], int blockCount, int processes[], int processCount) {
    int remaining[100];
    int allocation[100];

    for (int i = 0; i < blockCount; i++) {
        remaining[i] = blocks[i];
    }

    for (int i = 0; i < processCount; i++) {
        allocation[i] = -1;
        int worst = -1;

        for (int j = 0; j < blockCount; j++) {
            if (remaining[j] >= processes[i]) {
                if (worst == -1 || remaining[j] > remaining[worst]) {
                    worst = j;
                }
            }
        }

        if (worst != -1) {
            allocation[i] = worst;
            remaining[worst] = remaining[worst] - processes[i];
        }
    }

    cout << "\nWorst Fit:\n";
    cout << "Process\tSize\tBlock\n";

    for (int i = 0; i < processCount; i++) {
        cout << "P" << i + 1 << "\t" << processes[i] << "\t";

        if (allocation[i] == -1) {
            cout << "Not Allocated";
        }
        else {
            cout << "B" << allocation[i] + 1;
        }

        cout << endl;
    }
}

// Next Fit
void nextFit(int blocks[], int blockCount, int processes[], int processCount) {
    int remaining[100];
    int allocation[100];

    for (int i = 0; i < blockCount; i++) {
        remaining[i] = blocks[i];
    }

    int start = 0;

    for (int i = 0; i < processCount; i++) {
        allocation[i] = -1;

        int count = 0;
        int j = start;

        while (count < blockCount) {
            if (remaining[j] >= processes[i]) {
                allocation[i] = j;
                remaining[j] = remaining[j] - processes[i];
                start = j;
                break;
            }

            j = (j + 1) % blockCount;
            count++;
        }
    }

    cout << "\nNext Fit:\n";
    cout << "Process\tSize\tBlock\n";

    for (int i = 0; i < processCount; i++) {
        cout << "P" << i + 1 << "\t" << processes[i] << "\t";

        if (allocation[i] == -1) {
            cout << "Not Allocated";
        }
        else {
            cout << "B" << allocation[i] + 1;
        }

        cout << endl;
    }
}

int main() {
    int blockCount;
    int processCount;

    int blocks[100];
    int processes[100];

    cout << "Enter number of memory blocks: ";
    cin >> blockCount;

    cout << "Enter sizes of memory blocks:\n";

    for (int i = 0; i < blockCount; i++) {
        cout << "Block " << i + 1 << ": ";
        cin >> blocks[i];
    }

    cout << "\nEnter number of processes: ";
    cin >> processCount;

    cout << "Enter sizes of processes:\n";

    for (int i = 0; i < processCount; i++) {
        cout << "Process " << i + 1 << ": ";
        cin >> processes[i];
    }

    firstFit(blocks, blockCount, processes, processCount);
    bestFit(blocks, blockCount, processes, processCount);
    worstFit(blocks, blockCount, processes, processCount);
    nextFit(blocks, blockCount, processes, processCount);

    return 0;
}
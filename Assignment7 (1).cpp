#include <iostream>
#include <iomanip>
using namespace std;

int main() {
    int pages[50];
    int memory[20];
    int lastUsed[20];
    int table[20][50];
    char status[50];

    int n, frames, choice;
    int i, j, k;
    int found, position;
    int hits = 0, faults = 0;
    int pointer = 0;

    cout << "Enter number of pages (maximum 50): ";
    cin >> n;

    if (n < 1 || n > 50) {
        cout << "Invalid number of pages.";
        return 0;
    }

    cout << "Enter the page reference string:\n";

    for (i = 0; i < n; i++) {
        cin >> pages[i];
    }

    cout << "Enter number of frames (maximum 20): ";
    cin >> frames;

    if (frames < 1 || frames > 20) {
        cout << "Invalid number of frames.";
        return 0;
    }

    cout << "\n--- PAGE REPLACEMENT MENU ---\n";
    cout << "1. FCFS\n";
    cout << "2. LRU\n";
    cout << "3. Optimal\n";
    cout << "Enter your choice: ";
    cin >> choice;

    if (choice < 1 || choice > 3) {
        cout << "Invalid choice.";
        return 0;
    }

    for (i = 0; i < frames; i++) {
        memory[i] = -1;
        lastUsed[i] = -1;
    }

    for (i = 0; i < n; i++) {
        found = 0;
        position = -1;

        // Check whether the page is already in memory
        for (j = 0; j < frames; j++) {
            if (memory[j] == pages[i]) {
                found = 1;
                position = j;
                break;
            }
        }

        if (found == 1) {
            status[i] = 'H';

            if (choice == 2) {
                lastUsed[position] = i;
            }
        }
        else {
            status[i] = 'M';
            faults++;

            // Find an empty frame
            for (j = 0; j < frames; j++) {
                if (memory[j] == -1) {
                    position = j;
                    break;
                }
            }

            // FCFS: replace pages in arrival order
            if (position == -1 && choice == 1) {
                position = pointer;
            }

            // LRU: replace the least recently used page
            if (position == -1 && choice == 2) {
                position = 0;

                for (j = 1; j < frames; j++) {
                    if (lastUsed[j] < lastUsed[position]) {
                        position = j;
                    }
                }
            }

            // Optimal: replace the page used farthest in the future
            if (position == -1 && choice == 3) {
                int farthest = -1;

                for (j = 0; j < frames; j++) {
                    int nextUse = -1;

                    for (k = i + 1; k < n; k++) {
                        if (memory[j] == pages[k]) {
                            nextUse = k;
                            break;
                        }
                    }

                    if (nextUse == -1) {
                        position = j;
                        break;
                    }

                    if (nextUse > farthest) {
                        farthest = nextUse;
                        position = j;
                    }
                }
            }

            memory[position] = pages[i];

            if (choice == 1) {
                pointer++;

                if (pointer == frames) {
                    pointer = 0;
                }
            }

            if (choice == 2) {
                lastUsed[position] = i;
            }
        }

        // Save frame contents after each page request
        for (j = 0; j < frames; j++) {
            table[j][i] = memory[j];
        }
    }

    // Display the result table
    cout << "\n";

    if (choice == 1)
        cout << "FCFS PAGE REPLACEMENT\n";
    else if (choice == 2)
        cout << "LRU PAGE REPLACEMENT\n";
    else
        cout << "OPTIMAL PAGE REPLACEMENT\n";

    cout << left << setw(12) << "Page";

    for (i = 0; i < n; i++) {
        cout << setw(5) << pages[i];
    }

    cout << "\n";

    for (i = 0; i < frames; i++) {
        cout << left << setw(12) << ("Frame " + to_string(i + 1));

        for (j = 0; j < n; j++) {
            if (table[i][j] == -1)
                cout << setw(5) << "-";
            else
                cout << setw(5) << table[i][j];
        }

        cout << "\n";
    }

    cout << left << setw(12) << "H / M";

    for (i = 0; i < n; i++) {
        cout << setw(5) << status[i];

        if (status[i] == 'H')
            hits++;
    }

    cout << "\n";

    cout << "\nTotal Hits = " << hits;
    cout << "\nTotal Page Faults (Misses) = " << faults << "\n";

    return 0;
}

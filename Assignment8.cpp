#include <iostream>
#include <cmath>
using namespace std;

int main() {
    int n, a[50], head, size, dir;
    int i, j, temp, cur, total;

    cout << "Enter number of requests: ";
    cin >> n;

    cout << "Enter requests: ";
    for (i = 0; i < n; i++)
        cin >> a[i];

    cout << "Enter initial head position: ";
    cin >> head;

    cout << "Enter disk size: ";
    cin >> size;

    cout << "Enter direction (0=Left, 1=Right): ";
    cin >> dir;

    // FIFO
    cur = head;
    total = 0;
    cout << "\nFIFO: " << cur;
    for (i = 0; i < n; i++) {
        total += abs(cur - a[i]);
        cur = a[i];
        cout << " -> " << cur;
    }
    cout << "\nTotal movement = " << total << endl;

    // SSTF
    int done[50] = {0};
    cur = head;
    total = 0;
    cout << "\nSSTF: " << cur;

    for (i = 0; i < n; i++) {
        int min = 9999, pos = -1;
        for (j = 0; j < n; j++) {
            if (!done[j] && abs(cur - a[j]) < min) {
                min = abs(cur - a[j]);
                pos = j;
            }
        }
        done[pos] = 1;
        total += abs(cur - a[pos]);
        cur = a[pos];
        cout << " -> " << cur;
    }
    cout << "\nTotal movement = " << total << endl;

    // Sort requests using bubble sort
    for (i = 0; i < n - 1; i++)
        for (j = 0; j < n - i - 1; j++)
            if (a[j] > a[j + 1]) {
                temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
            }

    // SCAN
    cur = head;
    total = 0;
    cout << "\nSCAN: " << cur;

    if (dir == 1) {
        for (i = 0; i < n; i++)
            if (a[i] >= head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }

        total += abs(cur - (size - 1));
        cur = size - 1;
        cout << " -> " << cur;

        for (i = n - 1; i >= 0; i--)
            if (a[i] < head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }
    } else {
        for (i = n - 1; i >= 0; i--)
            if (a[i] <= head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }

        total += cur;
        cur = 0;
        cout << " -> 0";

        for (i = 0; i < n; i++)
            if (a[i] > head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }
    }
    cout << "\nTotal movement = " << total << endl;

    // C-SCAN
    cur = head;
    total = 0;
    cout << "\nC-SCAN: " << cur;

    if (dir == 1) {
        for (i = 0; i < n; i++)
            if (a[i] >= head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }

        total += abs(cur - (size - 1));
        cur = size - 1;
        cout << " -> " << cur;

        total += size - 1;
        cur = 0;
        cout << " -> 0";

        for (i = 0; i < n; i++)
            if (a[i] < head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }
    } else {
        for (i = n - 1; i >= 0; i--)
            if (a[i] <= head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }

        total += cur;
        cur = 0;
        cout << " -> 0";

        total += size - 1;
        cur = size - 1;
        cout << " -> " << cur;

        for (i = n - 1; i >= 0; i--)
            if (a[i] > head) {
                total += abs(cur - a[i]);
                cur = a[i];
                cout << " -> " << cur;
            }
    }

    cout << "\nTotal movement = " << total << endl;

    return 0;
}

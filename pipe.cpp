#include <iostream>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <cstring>

using namespace std;

int main() {
    int fd[2];
    
    if (pipe(fd) == -1) {
        perror("pipe");
        return 1;
    }

    pid_t pid = fork();

    if (pid < 0) {
        perror("fork");
        return 1;
    }

    if (pid > 0) {
        
        close(fd[0]);

        const char *message = "Hello from Parent!";
        
        write(fd[1], message, strlen(message) + 1);
       
        close(fd[1]);
        
        wait(NULL);
        cout << "Parent: Message sent successfully." << endl;
    }

    else {
        
        close(fd[1]);

        char buffer[100];
        
        read(fd[0], buffer, sizeof(buffer));

        cout << "Child received: " << buffer << endl;

        close(fd[0]);
    }

    return 0;
}
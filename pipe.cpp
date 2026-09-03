#include <iostream>
#include <unistd.h>
#include <sys/wait.h>
#include <cstring>

using namespace std;

int main () {
    int fd[2];
    
    pid_t pid = fork();
    
    if(pid < 0) {
        perror("fork");
        return 1;
    }
    
    if(pid > 0) {
        close(fd[0]);
        const char *message = "Hello from parent";
        write(fd[1],message,strlen(message) + 1);
        close(fd[1]);
        wait(NULL);
    }
    else {
        close(fd[1]);
        char buffer[100];
        read(fd[0],buffer,sizeof(buffer));
        cout << "\nChild received: " << buffer << endl;
        close(fd[0]);
    }
    return 0;
}
package Lab8;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<Workable> teamMembers = new ArrayList<>();
    private BackendService backendService; // DIP

    public Project(BackendService backendService) {
        this.backendService = backendService;
    }

    public void addTeamMember(Workable member) {
        teamMembers.add(member);
    }

    public void startProject() {
        System.out.println("Starting project with backend: " + backendService.getClass().getSimpleName());

        for (Workable member : teamMembers) {
            member.work();

            if (member instanceof Coder) {
                ((Coder) member).code();
            }

            if (member instanceof Designer) {
                ((Designer) member).designUI();
            }
        }

        System.out.println("Project data: " + backendService.getData());
    }
}
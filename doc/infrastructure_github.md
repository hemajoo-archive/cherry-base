# Cherry Base - GitHub Infrastructure

<hr>

The **GitHub** platform is used to store the project's source code, as a continuous development, integration and deployment platform.

## Code

<hr>

## Registries

<hr>

### Maven Registry

<hr>

### Docker Registry

<hr>

### Organization Secrets

| Name                                 | Description                                                                          |
|--------------------------------------|--------------------------------------------------------------------------------------|
| `DEPLOY_MAVEN_REGISTRY_GITHUB_USER`  | GitHub user authorized to publish artifacts in the Hemajoo Commerce Maven registry.  |
| `DEPLOY_MAVEN_REGISTRY_GITHUB_TOKEN` | GitHub token authorized to publish artifacts in the Hemajoo Commerce Maven registry. |
| `READ_MAVEN_REGISTRY_GITHUB_USER`    | GitHub user authorized to read artifacts in the Hemajoo Commerce Maven registry.     |
| `READ_MAVEN_REGISTRY_GITHUB_TOKEN`   | GitHub token authorized to read artifacts in the Hemajoo Commerce Maven registry.    |
| `SONAR_HOST_URL`                     | Sonar URL used to publish SAT analysis for Hemajoo Commerce projects.                |
| `SONAR_TOKEN`                        | Sonar token authorized to publish SAT analysis for hemajoo Commerce projects.        |

### Project Secrets

| Secret Name         | Secret Description                                                              |
|---------------------|---------------------------------------------------------------------------------|
| `SONAR_TOKEN`       | Sonar token authorized to publish SAT analysis for the **Cherry Base** project. |

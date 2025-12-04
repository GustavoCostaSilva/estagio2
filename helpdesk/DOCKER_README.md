# HelpDesk - Docker Deployment Guide

## 📋 Prerequisites
- Docker Engine 20.10+
- Docker Compose 1.29+

## 🚀 Quick Start

### Local Development
```bash
# Navigate to project directory
cd helpdesk

# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f api

# Stop all services
docker-compose down

# Stop and remove volumes (clean state)
docker-compose down -v
```

## 🔧 Configuration

### Environment Variables
All configuration is injected via environment variables in `docker-compose.yml`:

| Variable | Default Value | Description |
|----------|--------------|-------------|
| `SPRING_PROFILES_ACTIVE` | `dev` | Active Spring profile |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://db:3306/helpdesk...` | Database connection string |
| `SPRING_DATASOURCE_USERNAME` | `root` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | `helpdesk_root_password` | Database password |
| `JWT_SECRET` | `your_jwt_secret_key_here...` | JWT secret key |
| `JWT_EXPIRATION` | `1209600000` | JWT expiration time (ms) |

### Production Deployment
For production (Railway/Render), update the following in `docker-compose.yml`:
1. **JWT_SECRET**: Use a strong, randomly generated secret
2. **MYSQL_ROOT_PASSWORD**: Use a secure password
3. **SPRING_DATASOURCE_PASSWORD**: Match with MySQL password

## 🐳 Docker Services

### Database (db)
- Image: `mysql:8.0`
- Port: `3307:3306` (external:internal)
- Volume: `mysql_data` for persistence
- Healthcheck: Ensures MySQL is ready before API starts

### API (api)
- Build: Multi-stage Dockerfile
- Port: `8080:8080`
- Depends on: `db` service (waits for healthcheck)

## 📦 Build Details

### Multi-Stage Dockerfile
1. **Build Stage**: Uses `maven:3.8.6-openjdk-11-slim` to compile application
2. **Runtime Stage**: Uses `openjdk:11-jre-slim` for minimal production image

### Network
- Custom bridge network: `helpdesk-network`
- Internal DNS: Services communicate via service names (`db`, `api`)

## 🔍 Useful Commands

```bash
# Rebuild without cache
docker-compose build --no-cache

# View running containers
docker-compose ps

# Execute command in api container
docker-compose exec api bash

# View database logs
docker-compose logs db

# Remove all containers and volumes
docker-compose down -v --remove-orphans
```

## ✅ Verification

Access the application:
- **API**: http://localhost:8080
- **Swagger UI** (if enabled): http://localhost:8080/swagger-ui.html
- **MySQL**: `localhost:3307` (use MySQL Workbench or similar)

## 🚨 Troubleshooting

### API fails to connect to database
- Ensure MySQL healthcheck is passing: `docker-compose ps`
- Check database logs: `docker-compose logs db`

### Port already in use
- Change external port in `docker-compose.yml` (e.g., `8081:8080`)

### Build fails
- Clear Maven cache: `docker-compose build --no-cache`
- Check Java version in pom.xml matches Dockerfile (Java 11)

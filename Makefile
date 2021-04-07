clean:
	-rm -rf target/
	-docker image rm group-project_app
	-docker container stop world-database

deploy-local: clean
	mvn package -DskipTests
	docker compose up

clean:
	-rm -rf target/
	-docker image rm group-project_app

deploy-local: clean
	mvn package -DskipTests
	docker compose up

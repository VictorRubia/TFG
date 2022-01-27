#!/bin/bash

echo -ne '#                         (0%)\r'

mkdir prueba

echo -ne '##                        (15%)\r'

echo "# syntax=docker/dockerfile:1
FROM ruby:3.1
RUN apt-get update -qq && apt-get install -y nodejs postgresql-client sqlite3 default-mysql-client default-libmysqlclient-dev
WORKDIR /myapp
COPY Gemfile /myapp/Gemfile
COPY Gemfile.lock /myapp/Gemfile.lock
RUN bundle install

# Add a script to be executed every time the container starts.
COPY entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]
EXPOSE 3000

# Configure the main process to run when running the image
CMD [\"rails\", \"server\", \"-b\", \"0.0.0.0\"]" > prueba/Dockerfile

echo -ne '###                       (25%)\r'

echo "source \"https://rubygems.org\"
gem \"rails\", \"~> 7.0.1\"
gem \"mysql2\", \"~> 0.5\"" > prueba/Gemfile

touch prueba/Gemfile.lock
echo -ne '#####                     (45%)\r'
echo "#!/bin/bash
set -e

# Remove a potentially pre-existing server.pid for Rails.
rm -f /myapp/tmp/pids/server.pid

# Then exec the container's main process (what's set as CMD in the Dockerfile).
exec \"\$@\"" > prueba/entrypoint.sh

echo "version: \"3.9\"
services:
  db:
    image: mysql:5.7
    command: --max_connections=10000
    environment:
      MYSQL_ROOT_PASSWORD: admin
      #MYSQL_DATABASE: tfg
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin
    volumes:
      - ./data/db:/var/lib/mysql
    # image: postgres
    # volumes:
    #   - ./tmp/db:/var/lib/postgresql/data
    # environment:
    #   POSTGRES_PASSWORD: password
  phpmyadmin:
    image: phpmyadmin/phpmyadmin
    container_name: pma_tfg
    links:
      - db
    environment:
      PMA_HOST: db
      PMA_PORT: 3306
      PMA_ARBITRARY: 1
    restart: always
    ports:
      - 8081:80
  web:
    build: .
    command: bash -c \"rm -f tmp/pids/server.pid && bundle exec rails s -p 3000 -b '0.0.0.0'\"
    volumes:
      - .:/myapp
    ports:
      - \"3000:3000\"
    depends_on:
      - db" > prueba/docker-compose.yml
cd prueba
docker-compose run --no-deps web rails new . --force --database=mysql
echo -ne '###############           (65%)\r'
docker-compose build -f prueba/Dockerfile

rm prueba/config/database.yml

echo "# MySQL. Versions 5.5.8 and up are supported.
#
# Install the MySQL driver
#   gem install mysql2
#
# Ensure the MySQL gem is defined in your Gemfile
#   gem \"mysql2\"
#
# And be sure to use new-style password hashing:
#   https://dev.mysql.com/doc/refman/5.7/en/password-hashing.html
#
default: &default
  adapter: mysql2
  encoding: utf8mb4
  pool: <%= ENV.fetch(\"RAILS_MAX_THREADS\") { 5 } %>
  username: root
  password: admin
  host: db

development:
  <<: *default
  database: myapp_development

# Warning: The database defined as \"test\" will be erased and
# re-generated from your development database when you run \"rake\".
# Do not set this db to the same as development or production.
test:
  <<: *default
  database: myapp_test

# As with config/credentials.yml, you never want to store sensitive information,
# like your database password, in your source code. If your source code is
# ever seen by anyone, they now have access to your database.
#
# Instead, provide the password or a full connection URL as an environment
# variable when you boot the app. For example:
#
#   DATABASE_URL=\"mysql2://myuser:mypass@localhost/somedatabase\"
#
# If the connection URL is provided in the special DATABASE_URL environment
# variable, Rails will automatically merge its configuration values on top of
# the values provided in this file. Alternatively, you can specify a connection
# URL environment variable explicitly:
#
#   production:
#     url: <%= ENV[\"MY_APP_DATABASE_URL\"] %>
#
# Read https://guides.rubyonrails.org/configuring.html#configuring-a-database
# for a full overview on how database connection configuration can be specified.
#
production:
  <<: *default
  database: myapp_production
" > prueba/config/database.yml

echo -ne '######################### (100%)\r'

docker-compose -f ./prueba/docker-compose.yml up -d

docker-compose run web rake db:create

echo "DONE!"
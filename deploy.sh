echo "Building app..."
./mvnw clean package

echo "Deploy files to server..."
scp -r -i "C:\Users\asus\Desktop\ssh" target/be.jar root@143.198.196.146:/var/www/be/

ssh -i "C:\Users\asus\Desktop\ssh" root@143.198.196.146 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/be
java -jar be.jar
EOF
exit
echo "Done!"
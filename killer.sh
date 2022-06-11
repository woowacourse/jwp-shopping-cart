pid=$(lsof -i:8080 | sed -n '2p' | cut -f 5 -d ' ')
# pid=$(pgrep -f jwp-shopping-cart)
if [ -n"${pid}" ]
then
       kill -9 ${pid}
       echo killed process ${pid}
else
       echo no process
fi

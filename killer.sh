pid=$(lsof -i:8080 | sed -n '2p' | tr -s ' ' | cut -f 2 -d ' ')
# pid=$(pgrep -f jwp-shopping-cart)
if [ -n"${pid}" ]
then
       kill -9 ${pid}
       echo killed process ${pid}
else
       echo no process
fi

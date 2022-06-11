pid=$(lsof -i:$1 | sed -n '2p' | cut -f 5 -d ' ')
# pid=$(pgrep -f jwp-shopping-cart)
if [ -n"${pid}" ]
then
       kill -9 ${pid}
       echo kill process ${pid}
else
       echo no process
fi
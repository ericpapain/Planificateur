#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Jun  2 10:22:48 2020

@author: eric
"""


#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May 29 18:01:26 2020

@author: eric
"""

"""
import numpy as np
import matplotlib.pyplot as plt

x_tfd1 = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]
x_tfd2 = [20]
x_tfd3 = [21,22,23]
x_tfd4 = [24]
x_tfd5 = [25,26,27,28,29,30]

x_pandas_1 = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
x_pandas_2 = [24]


y_tfd1 = [0.53,0.71,0.69,0.83,1.13,0.94,1.09,1.25,1.22,1.24,1.11,1.03,1.09,1.52,1.47,2.81,2.52,3.29,3.18]
y_tfd2 = [0]
y_tfd3 = [2.82,2.48,4.04]
y_tfd4 = [0]
y_tfd5 = [8.31,10.30,5.6,19.43,17.68,25.15]

y_pandas_1 = [0.1,0.3,0.31,0.57,1.25,1.63,1.69,1.81,0.4,0.41,0.36,0.78,0.48,8.01,0.66]
y_pandas_2 = [3.64]


plt.plot(x_tfd1, y_tfd1, "r-.^", label="TFDPLANNER")
plt.plot(x_tfd3, y_tfd3, "r-.^")
plt.plot(x_tfd5, y_tfd5, "r-.^")


'plt.plot(x_tfd, y_tfd3, "r-.>", label="TFDPLANNER")'
'plt.plot(x_tfd1, y_tfd5, "r-.>", label="TFDPLANNER")'
'plt.plot(x_tfd2, y_tfd, "r-.s", label="TFDPLANNER")'


plt.plot(x_pandas_1, y_pandas_1, "b:v", label="PANDA-SHOP")
plt.plot(x_pandas_2, y_pandas_2, "b:v")



plt.xlabel("Problems-transport")
plt.ylabel("(time in seconde)x10")
plt.title("Time Curve TFDPLANNER vs PANDA")
plt.legend()

plt.show()
"""
import numpy as np
import matplotlib.pyplot as plt

x_tfd1 = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]
x_tfd2 = [20]
x_tfd3 = [21,22,23]
x_tfd4 = [24]
x_tfd5 = [25,26,27,28,29,30]

x_pandas_1 = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
x_pandas_2 = [24]


y_tfd1 = [8,19,16,23,34,31,34,37,38,36,20,18,29,36,43,59,54,59,73,]
y_tfd2 = [0]
y_tfd3 = [57,71,73]
y_tfd4 = [0]
y_tfd5 = [90,145,93,173,171,137]

y_pandas_1 = [8,19,15,22,32,29,32,34,31,35,20,18,27,32,40]
y_pandas_2 = [72]


plt.plot(x_tfd1, y_tfd1, "r-.o", label="TFDPLANNER")
plt.plot(x_tfd3, y_tfd3, "r-.o")
plt.plot(x_tfd5, y_tfd5, "r-.o")


'plt.plot(x_tfd, y_tfd3, "r-.>", label="TFDPLANNER")'
'plt.plot(x_tfd1, y_tfd5, "r-.>", label="TFDPLANNER")'
'plt.plot(x_tfd2, y_tfd, "r-.s", label="TFDPLANNER")'


plt.plot(x_pandas_1, y_pandas_1, "b:v", label="PANDA-SHOP")
plt.plot(x_pandas_2, y_pandas_2, "b:v")



plt.xlabel("Problems-transport")
plt.ylabel("costs")
plt.title("Costs Curve TFDPLANNER vs PANDA")
plt.legend()

plt.show()

"""
TIME
transport
    tfd = [0.53,0.71,0.69,0.83,1.13,0.94,1.09,1.25,1.22,1.24,1.11,1.03,1.09,1.52,1.47,2.81,2.52,3.29,3.18][-1][2.82,2.48,4.04][-1][8.31,10.30,5.6,19.43,17.68,25.15]
    panda = [0.1,0.3,0.31,0.57,1.25,1.63,1.69,1.81,0.4,0.41,0.36,0.78,0.48,8.01,0.66][-1,-1,-1,-1,-1,-1,-1,-1][3.64][-1,-1,-1,-1,-1,-1]
"""

"""
COUT

transport
    tfd = [8,19,16,23,34,31,34,37,38,36,20,18,29,36,43,59,54,59,73,-1,57,71,73,-1,90,145,93,173,171,137]
    panda = [8,19,15,22,32,29,32,34,31,35,20,18,27,32,40,-1,-1,-1,-1,-1,-1,-1,-1,72,-1,-1,-1,-1,-1,-1]

"""
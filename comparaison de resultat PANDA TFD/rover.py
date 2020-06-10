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


import numpy as np
import matplotlib.pyplot as plt

x_tfd = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
x_pandas_1 = [1,2,3,4,5]
x_pandas_2 = [6]
x_pandas_3 = [7,8]
x_pandas_4 = [9,10,11]
x_pandas_5 = [12]
x_pandas_6 = [13,14,15,16,17,18,19,20]

y_tfd = [1.37,0.69,0.94,0.94,1.1,1.42,1.48,1.39,3.43,1.53,1.81,2.04,2.52,2.33,3.21,2.86,4.83,6.62,7.82,13.06]

y_pandas_1 = [1.26,0.44,0.33,0.25,25.60]
y_pandas_2 = [0]
y_pandas_3 = [11.89,19.56]
y_pandas_4 = [0,0,0]
y_pandas_5 = [5.84]
y_pandas_6 = [0,0,0,0,0,0,0,0]


plt.plot(x_tfd, y_tfd, "r-.>", label="TFDPLANNER")

plt.plot(x_pandas_1, y_pandas_1, "b:^", label="PANDA")
'plt.plot(x_pandas_2, y_pandas_2, "b:>", label="PANDA")'
plt.plot(x_pandas_3, y_pandas_3, "b:^")
'plt.plot(x_pandas_4, y_pandas_4, "b:>", label="PANDA")'
plt.plot(x_pandas_5, y_pandas_5, "b:^")
'plt.plot(x_pandas_6, y_pandas_6, "b:>", label="PANDA")'

plt.xlabel("Problems-rover")
plt.ylabel("(time in second)x10")
plt.title("Time Curve TFDPLANNER vs PANDA")
plt.legend()

plt.show()

"""
import numpy as np
import matplotlib.pyplot as plt

x_tfd = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
x_pandas_1 = [1,2,3,4,5]
x_pandas_2 = [6]
x_pandas_3 = [7,8]
x_pandas_4 = [9,10,11]
x_pandas_5 = [12]
x_pandas_6 = [13,14,15,16,17,18,19,20]

y_tfd = [12,10,12,13,26,46,20,39,62,42,41,21,54,35,42,52,64,72,114,133]

y_pandas_1 = [28,24,28,24,63]
y_pandas_2 = [0]
y_pandas_3 = [49,69]
y_pandas_4 = [0,0,0]
y_pandas_5 = [52]
y_pandas_6 = [0,0,0,0,0,0,0,0]


plt.plot(x_tfd, y_tfd, "r-.s", label="TFDPLANNER")

plt.plot(x_pandas_1, y_pandas_1, "b:o", label="PANDA-SHOP")
'plt.plot(x_pandas_2, y_pandas_2, "b:>", label="PANDA")'
plt.plot(x_pandas_3, y_pandas_3, "b:o")
'plt.plot(x_pandas_4, y_pandas_4, "b:>", label="PANDA-SHOP")'
plt.plot(x_pandas_5, y_pandas_5, "b:o")
'plt.plot(x_pandas_6, y_pandas_6, "b:>", label="PANDA")'

plt.xlabel("Problems-rover")
plt.ylabel("Costs")
plt.title("Costs Curve TFDPLANNER vs PANDA")
plt.legend()

plt.show()
"""

"""
TIME
rover
    tfd = [1.37,0.69,0.94,0.94,1.1,1.42,1.48,1.39,3.43,1.53,1.81,2.04,2.52,2.33,3.21,2.86,4.83,6.62,7.82,13.06]
    
    panda = [1.26,0.44,0.33,0.25,88.60][-1][14.89,21.56][-1,-1,-1][58.4][-1,-1,-1,-1,-1,-1,-1,-1]

"""

"""
COUT
rover
    tfd = [12,10,12,13,26,46,20,39,62,42,41,21,54,35,42,52,64,72,114,133]
    panda-shop = [28,24,28,24,63,-1,49,69,-1,-1,-1,52,-1,-1,-1,-1,-1,-1,-1,-1]
"""
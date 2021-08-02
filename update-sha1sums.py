#!/usr/bin/env python
#
# Copyright (C) 2016 The CyanogenMod Project
# Copyright (C) 2017-2020 The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import os
import sys
from hashlib import sha1

device='mido'
vendor='xiaomi'
filename='proprietary-files.txt'

with open(filename, 'r') as f:
    lines = f.read().splitlines()
vendorPath = '../../../vendor/' + vendor + '/' + device + '/proprietary'
needSHA1 = False


def cleanup():
    for index, line in enumerate(lines):
        # Skip empty or commented lines
        if len(line) == 0 or line[0] == '#' or '|' not in line:
            continue

        # Drop SHA1 hash, if existing
        lines[index] = line.split('|')[0]


def update():
    for index, line in enumerate(lines):
        # Skip empty lines
        if len(line) == 0:
            continue

        # Check if we need to set SHA1 hash for the next files
        if line[0] == '#':
            needSHA1 = (' - from' in line)
            continue

        if needSHA1:
            # Remove existing SHA1 hash
            line = line.split('|')[0]

            filePath = line.split(';')[0].split(':')[-1]
            if filePath[0] == '-':
                filePath = filePath[1:]

            with open(os.path.join(vendorPath, filePath), 'rb') as f:
                hash = sha1(f.read()).hexdigest()

            lines[index] = '%s|%s' % (line, hash)

def arrange(x):
    file=open(x,'r')
    f=file.read()
    g=f.split("\n\n")
    new_txt=""
    dash=""
    tags=[]
    text=[]
    for i in range(0,len(g)):
        s=g[i]
        s=s.split("\n")
        for k in range(0,len(s)):
            if len(s[k]) > 0:
                if s[k][0] == '-':
                    s[k]=s[k][1:]
                    tags.append(s[k])
        s.sort(key=str.lower)
        for j in range(0,len(s)):
            if len(s[j]) > 0:
                if s[j] in tags:
                    dash='-'
                if j == (len(s)-1):
                    new_txt+=dash + s[j] + '\n\n'
                else:
                    new_txt+=dash + s[j] + '\n'
            dash=''
        text.append(new_txt)
        new_txt=''

    text.sort(key=str.lower)

    if text[len(text)-1][:-4] == '\n\n':
        text[len(text)-1]=text[len(text)-1][:-4]
    elif text[len(text)-1][:-2] == '\n':
        text[len(text)-1]=text[len(text)-1][:-2]

    file.close()

    file=open(x,'w')
    for r in text:
        file.writelines(r)
    file.close()

if len(sys.argv) == 2 and sys.argv[1] == '-c':
    cleanup()
else:
    update()

with open(filename, 'w') as file:
    file.write('\n'.join(lines) + '\n')

arrange(filename)

import json

kk = {
    "123": [
        {
            "kk": "dfsa",
            "ll": "sdf",
            "info": [
                {
                    "cate": "1",
                    "sdfjlk": "sdf",
                },
                {
                    "cate": "9",
                    "sdfjlk": "sdsdf",
                },
            ]
        },
        {
            "oo": "dsflkj",
            "pp": "sdfjalk;j",
            "info": [
                {
                    "cate": "17",
                    "sdfjlk": "sdghf",
                },
                {
                    "cate": "9",
                    "sdfjlk": "sdsddfgf",
                },
            ]
        },
    ],
    "124": [
        {
            "kk": "dfddsa",
            "ll": "sdddf",
            "info": [
                {
                    "cate": "1",
                    "sdfjlk": "sdf",
                },
                {
                    "cate": "9",
                    "sdfjlk": "sdsdf",
                },
            ]
        },
    ],
}

ko = {"123": [
    {
        "kk": "dfsdsa",
        "ll": "sdfd",
        "info": [
            {
                "cate": "1",
                "sdfjlk": "sdf",
            },
            {
                "cate": "9",
                "sdfjlk": "sdsdf",
            },
        ]
    },
    {
        "oo": "dsflkj",
        "pp": "sdfjalk;j",
        "info": [
            {
                "cate": "17",
                "sdfjlk": "sdghf",
            },
            {
                "cate": "9",
                "sdfjlk": "sdsddfgf",
            },
        ]
    },
], }
kk['123'].append(ko['123'])
print(json.dumps(kk))
